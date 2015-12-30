package com.jsonengine.data;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import com.jsonengine.exception.JEErrorCodeEnum;
import com.jsonengine.exception.JEIllegalOverrideException;
import com.jsonengine.exception.JERuntimeException;
import com.jsonengine.exception.JEUninitialDataException;

public class DataOptional<T> {

	private T					data;

	private DataStatus			status;

	private JERuntimeException	traceError;

	private final Lock			lock			= new ReentrantLock();

	private final Condition		processing		= lock.newCondition();

	// Set default timeout as 5 second
	private final static long	DEFAULT_TIMEOUT	= 5000;

	public DataOptional() {
		status = DataStatus.UNINITIAL;
	}

	public DataOptional( T data ) {
		status = data == null ? DataStatus.EMPTY : DataStatus.PRESENT;
		this.data = data;
	}

	public T get() {

		return get( DEFAULT_TIMEOUT );
	}

	public T get( long timeout ) {

		try {
			lock.lock();

			switch ( status ) {

				case UNINITIAL:
					throw new JEUninitialDataException( "Required data has been been initialized yet" );
				case EMPTY:
				case PRESENT:
					return data;
				case TIMEOUT:
				case FAILED:
					if ( traceError != null ) {
						throw traceError;
					} else {
						throw new JERuntimeException( JEErrorCodeEnum.INTERNAL_ERROR,
								String.format( "Required data cannot be set due to: %s", status ) );
					}
				case PROCESSING:
					processing.await( timeout, TimeUnit.MILLISECONDS );
					return data;
				default: // NEVER reach here
					return data;
			}
		}
		catch ( Exception e ) {
			throw new JERuntimeException( JEErrorCodeEnum.INTERNAL_ERROR, String.format(
					"Required data cannot be set due to: %s : %s", e.getClass().getSimpleName(), e.getMessage() ), e );
		}
		finally {
			lock.unlock();
		}
	}

	/**
	 * this is a best effort
	 * 
	 * @param status
	 * @param je
	 */
	public void traceError( DataStatus status, JERuntimeException je ) {

		try {
			lock.lock();

			if ( status == DataStatus.UNINITIAL || status == DataStatus.PROCESSING ) {
				this.status = status == null ? DataStatus.FAILED : status;
				this.traceError = je;

				this.processing.signalAll();
			}
		}
		catch ( Exception e ) {
			// do nothing
		}
		finally {
			lock.unlock();
		}
	}

	public void setData( T data ) {

		try {
			lock.lock();

			if ( status == DataStatus.UNINITIAL || status == DataStatus.PROCESSING ) {
				this.data = data;
				if ( data == null ) {
					status = DataStatus.EMPTY;
				} else {
					status = DataStatus.PRESENT;
				}

				this.processing.signalAll();
			} else {
				throw new JEIllegalOverrideException( "The data has already been set" );
			}
		}
		catch ( JERuntimeException je ) {
			throw je;
		}
		catch ( Exception e ) {
			throw new JERuntimeException( JEErrorCodeEnum.INTERNAL_ERROR, String.format(
					"Required data cannot be set due to: %s : %s", e.getClass().getSimpleName(), e.getMessage() ), e );
		}
		finally {
			lock.unlock();
		}
	}

	public DataStatus getStatus() {

		return status;
	}

	public boolean isPresent() {

		return status == DataStatus.PRESENT;
	}

	public boolean inProcessing() {

		return status == DataStatus.PROCESSING;
	}
}
