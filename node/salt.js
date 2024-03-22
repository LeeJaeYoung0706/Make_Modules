import crypto from "crypto"

/**
 * Crypto 모듈의 randomBytes 메소드를 통해 Salt를 반환하는 함수 작성
 * @returns {Promise<unknown>}
 */
const createSalt = () => {
    return new Promise((resolve, reject) => {
        crypto.randomBytes(64, (err, buf) => {
            if (err) reject(err)
            resolve(buf.toString('base64'));
        })
    })
}

/**
 * createSalt 함수로 salt를 생성하고 sha-512로 해싱한 암호화된 비밀번호 생성
 * @param plainPassword
 * @returns {Promise<unknown>}
 */
const createHashedPassword = (plainPassword) => {
    return new Promise(async (resolve, reject) => {
        const salt = await createSalt();
        crypto.pbkdf2(plainPassword, salt, 9999, 64, 'sha512',
            (err, key) => {
                if (err) reject(err)
                resolve({ password: key.toString('base64'), salt });
            })
    })
}

/**
 *
 * @param plainPassword
 * @param salt
 * @returns {Promise<unknown>}
 */
const makePasswordHashed = (plainPassword, salt) => {
    return new Promise(async (resolve, reject) => {
        crypto.pbkdf2(plainPassword, salt, 9999, 64, 'sha512',
            (err, key) => {
                if (err) reject(err)
                resolve({ password: key.toString('base64') });
            })
    })
}

module.exports = {
    createSalt,
    createHashedPassword,
    makePasswordHashed
}
