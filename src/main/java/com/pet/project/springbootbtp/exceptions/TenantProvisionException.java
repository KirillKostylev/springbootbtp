package com.pet.project.springbootbtp.exceptions;

public class TenantProvisionException extends RuntimeException {
    public TenantProvisionException(String message) {
        super(message);
    }

    public TenantProvisionException(String message, Throwable cause) {
        super(message, cause);
    }

    public TenantProvisionException(Throwable cause) {
        super(cause);
    }

    public TenantProvisionException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
