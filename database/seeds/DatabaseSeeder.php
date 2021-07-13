<?php


use Phinx\Seed\AbstractSeed;

class DatabaseSeeder extends AbstractSeed
{
    /**
     * Run Method.
     *
     * Write your database seeder using this method.
     *
     * More information on writing seeders is available here:
     * https://book.cakephp.org/phinx/0/en/seeding.html
     */
    public function run()
    {
        $roles = [
            ['name' => 'admin', 'description' => 'Administrator'],
            ['name' => 'curator', 'description' => 'Curator'],
        ];

        $this->table('roles')->insert($roles)->saveData();

        $roleFKs = $this->adapter->fetchAll("SELECT id FROM roles");

        $users = [
            ['first_name' => 'Admin', 'last_name' => '', 'email' => 'admin@cubanmusic.info', 'password' => sha1('1234'), 'role_id' => $roleFKs[0]['id']]
        ];

        $this->table('users')->insert($users)->saveData();
    }
}
