<?php
declare(strict_types=1);

use App\Domain\User\UserRepository;
use DI\ContainerBuilder;

use function DI\autowire;

return function (ContainerBuilder $containerBuilder) {
    // Here we map our UserRepository interface to its in memory implementation
    $containerBuilder->addDefinitions([
        'userRepository' => autowire(UserRepository::class),
    ]);
};
