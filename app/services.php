<?php

use App\Services\AuthService;
use DI\ContainerBuilder;

use function DI\autowire;

return function (ContainerBuilder $containerBuilder) {
    $containerBuilder->addDefinitions([
        'authService' => autowire(AuthService::class)
    ]);
};