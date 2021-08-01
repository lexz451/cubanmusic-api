<?php

namespace App\Repositories;

use App\Models\User;

class UserRepository extends BaseRepository {
    
    public function getByEmailAndPassword($email, $password) {
        $sha_password = sha1($password);
        return User::where('email', $email)->where('password', $sha_password)->first();
    }
}