const express = require ('express')
const conexion = require ('./database')
const app = express();

app.use(express.json())

app.post('/login', (req, res) => {
    const correo = req.body.correo
    const clave = req.body.clave
    console.log(correo)
    conexion.query('SELECT correo, clave FROM usuarios WHERE ? AND ?',[{correo:correo}, {clave:clave}],(error,results)=>{      
        if(error)
        {
            console.log(error)
        }else if(results!=null){
            console.log(results)
            res.status(200).send(JSON.stringify(results[0]))
        }
        else{
            console.log(results)
            res.status(404).send()
        }
    })
})

app.post('/register', (req, res) => {
    const codigo = req.body.codigo
    const nombre = req.body.nombre
    const correo = req.body.correo
    const clave = req.body.clave
    const Pseguridad = req.body.Pseguridad
    const Rseguridad = req.body.Rseguridad
    const Rol_id = req.body.Rol_id

    conexion.query(`INSERT INTO usuarios (codigo,nombre,correo,clave,Pseguridad,Rseguridad,Rol_id) VALUES('${codigo}',
    '${nombre}','${correo}','${clave}',${Pseguridad},'${Rseguridad}',${Rol_id})`,(error,results)=>{
    if(error)//brb
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send()
    }
    else{
        res.status(404).send()
    }
})
})


app.get('/getUser', (req,res)=>{
    const codigo = req.body.codigo
    console.log(codigo)
    conexion.query('SELECT * FROM usuarios WHERE ?',[{codigo}],(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        console.log(results)
        res.status(200).send(JSON.stringify(results[0]))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})

app.listen(3000, () => {
    console.log("Listening on port 3000...")
})