const mysql = require('mysql')

const conexion = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: '',
    database: 'sdjcomp2'
})

conexion.connect((error)=>{
    if(error){
        console.log(error)
        return
    }
    console.log('¡Conectado a la bd!')
})

module.exports = conexion