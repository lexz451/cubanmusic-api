<?php

declare(strict_types=1);

namespace App\Exceptions;

class UnauthorizedException extends BaseException {
    public function __construct(string $message = 'Unauthorized', int $code = 401, \Exception $previous = null) {
        parent::__construct($message, $code, $previous);
    }
}