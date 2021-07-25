<?php

namespace App\Services;

use App\Models\User;
use App\Repositories\UserRepository;
use DateTime;
use Firebase\JWT\JWT;
use Psr\Container\ContainerInterface;

class AuthService {

    private UserRepository $userRepository;

    public function __construct(ContainerInterface $containerInterface)
    {
        $this->userRepository = $containerInterface->get(UserRepository::class);
    }

    public function login($username, $password) {
        $user = $this->userRepository->getByEmailAndPassword($username, $password);
        if ($user) {
            $token = $this->generateToken($user);
            return $token;
        }
        return false;
    }

    public function generateToken(User $user) {
        $now = new DateTime();
        $future = new DateTime("+1 day");
        $payload = [
            'exp' => $future->getTimestamp(),
            'iat' => $now->getTimestamp(),
            'sub' => $user->id,
        ];
        $secret = $_ENV['JWT_SECRET'];
        return JWT::encode($payload, $secret, 'HS256');
    }
}