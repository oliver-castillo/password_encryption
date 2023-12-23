# Password Encryption API

## About

This is an API (Application Programming Interface) for password encryption developed using Spring as framework and Java as programming language.

## Supported algorithms

To encrypt a password it is necessary to use an algorithm. In this project, the supported algorithms are listed below.

- Argon2id
- Bcrypt
- PBKDF2
- Scrypt

## API Access Method

You can access the API through this link:

    https://passwordencryption-production.up.railway.app/api/v1/encrypt_password/{algorithm}

## API Usage

### Step 1: Select the algorithm

#### Argon2id, Bcrypt, PBKDF2 or Scrypt

To use the API with one of the supported algorithms, simply change {algorithm} to the lowercase algorithm name.

#### - Examples:

    https://passwordencryption-production.up.railway.app/api/v1/encrypt_password/argon2id

    https://passwordencryption-production.up.railway.app/api/v1/encrypt_password/bcrypt
    
    https://passwordencryption-production.up.railway.app/api/v1/encrypt_password/pbkdf2
    
    https://passwordencryption-production.up.railway.app/api/v1/encrypt_password/scrypt

### Step 2: Create and send the body

The **HTTP** method is **GET** and the body of the request must contain the password in **JSON** format.

#### - Example:

    {
      "password": "your_password"
    }

If you choose the **Argon2id** algorithm, it uses three different parameters:

 - Number of iterations
 - Number of memory in kilobytes
 - Number of parallelism factor

By default, the values of the parameters are 1, 1024 and 1 respectively and you only need to specify the password as in the example above. But if you want to custom the parameters, you need to send a JSON with the values.

#### - Example:

    {
        "password": "12345",
        "iterations": 2,
        "memory": 2048,
        "parallelism": 2
    }

The **iterations** value must be between **1** and **20**.

The **memory** value must be between **1024** and **100000**.

The **parallelism** value must be between **1** and **10**.

### Step 3: Get the encrypted password

If there was no problem, you will get the encrypted password and the name of the algorithm used in **JSON** format.

#### - Example:

    {
        "encryptedPassword": "**encrypted_password**",
        "algorithmUsed": "algorithm"
    }

Remember to review the documentation for additional details on using the API and its features. We hope you find this password encryption tool useful!