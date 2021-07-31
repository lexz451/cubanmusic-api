<?php

namespace App\Repositories;

use App\Models\User;
use Psr\Container\ContainerInterface;

class UserRepository extends BaseRepository {

    public function __construct(ContainerInterface $container) {
        parent::__construct($container, User::class);
    }
    
    public function getByEmailAndPassword($email, $password) {

        $role = User::where('email', $email)->first()->role;
        $this->logger->info('Role: ' . $role);

        $sha_password = sha1($password);
        return User::where('email', $email)->where('password', $sha_password)->first();
    }
}