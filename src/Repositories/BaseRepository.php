<?php

namespace App\Repositories;

use Psr\Container\ContainerInterface;
use Psr\Log\LoggerInterface;

class BaseRepository
{
    protected $logger;

    public function __construct(ContainerInterface $container) {
        $this->logger = $container->get(LoggerInterface::class);
    }
}