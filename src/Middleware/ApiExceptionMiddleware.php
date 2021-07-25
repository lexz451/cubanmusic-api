<?php

declare(strict_types=1);

namespace App\Middleware;

use App\Exceptions\BaseException;
use App\Exceptions\UnauthorizedException;
use Psr\Http\Message\ResponseFactoryInterface;
use Psr\Http\Message\ResponseInterface;
use Psr\Http\Message\ServerRequestInterface;
use Psr\Http\Server\MiddlewareInterface;
use Psr\Http\Server\RequestHandlerInterface;

class ApiExceptionMiddleware implements MiddlewareInterface {

    /** @var ResponseFactoryInterface */
    private $responseFactory;

    public function __construct(
        ResponseFactoryInterface $responseFactory
    )
    {
        $this->responseFactory = $responseFactory;
    }


    public function process(ServerRequestInterface $request, RequestHandlerInterface $handler): ResponseInterface {
        try {
            return $handler->handle($request);
        } catch (BaseException $e) {
            $response = $this->responseFactory->createResponse()
                ->withStatus((int)$e->getCode())
                ->withHeader('Content-Type', 'application/json');
            $result = [
                    'code' => (int)$e->getCode(),
                    'status' => 'error',
                    'message' => $e->getMessage(),
                ];
            $response->getBody()->write(json_encode($result, JSON_PRETTY_PRINT));
            return $response;
        }
    }
}