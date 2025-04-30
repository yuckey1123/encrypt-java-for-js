import crypto from 'crypto';

// javaで生成した値を入力
const ivBase64 = '';
const encryptedText = '';

// PASSWORDとSALTはjavaと同じ値を使うこと
const ALGO = 'aes-256-cbc';
const PASSWORD = '';
const SALT = '';

// Base64エンコードされたソルトをデコード
const saltBytes = Buffer.from(SALT, 'base64');

function decryptString(ivStr, encryptedDataStr) {
  try {
    // const key = crypto.scryptSync(PASSWORD, saltBytes, 32);
    const key = crypto.pbkdf2Sync(PASSWORD, saltBytes, 65536, 32, 'sha256');
    console.log("生成されたキー:", key.toString('hex'));

    const iv = Buffer.from(ivStr, 'base64');
    console.log("IV (バイナリ):", iv.toString('hex'));

    const encryptedData = Buffer.from(encryptedDataStr, 'base64');
    console.log("暗号化されたデータ (バイナリ):", encryptedData.toString('hex'));

    const decipher = crypto.createDecipheriv(ALGO, key, iv);
    let decryptedData = decipher.update(encryptedData, 'base64', 'utf8');
    decryptedData += decipher.final('utf8');
    console.log("復号されたデータ:", decryptedData);

    return decryptedData;
  } catch (err) {
    console.error("復号化エラー:", err);
    throw err;
  }
}

const decryptedMessage = decryptString(ivBase64, encryptedText);
console.log("復号データ:", decryptedMessage);
