<?php

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use Role;

class User extends Model {
    protected $table = 'users';

    public $timestamps = true;

    protected $fillable = [
        'first_name',
        'last_name',
        'email'
    ];

    protected $guarded = [
        'password'
    ];

    public function role()
    {
        return $this->hasOne(Role::class);
    }
}