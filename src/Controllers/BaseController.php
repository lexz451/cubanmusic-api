<?php

namespace App\Controllers;

use App\Repositories\UserRepository;
use App\Services\AuthService;
use Cake\Validation\Validator;
use DI\Container;
use Illuminate\Database\Capsule\Manager;
use Monolog\Logger;
use Psr\Container\ContainerInterface;
use Psr\Log\LoggerInterface;
use Selective\Validation\Converter\CakeValidationConverter;
use Selective\Validation\Factory\CakeValidationFactory;
use Selective\Validation\ValidationResult;
use Slim\Psr7\Request;
use Slim\Psr7\Response;

use function DI\get;

class BaseController
{
    protected CakeValidationFactory $validatorFactory;
    protected LoggerInterface $logger;
    protected AuthService $auth;

    public function __construct(ContainerInterface $container)
    {
        $this->logger = $container->get(LoggerInterface::class);
        $this->validatorFactory = $container->get(CakeValidationFactory::class);
        $this->auth = $container->get(AuthService::class);
    }

    protected function jsonResponse(
        Response $response,
        string $status,
        $message,
        int $code
    ): Response {
        $result = [
            'code' => $code,
            'status' => $status,
            'message' => $message
        ];
        $response->getBody()->write(json_encode($result, JSON_PRETTY_PRINT));
        return $response;
    }
}
