const express = require ('express')
const conexion = require ('./database')
const app = express();

app.use(express.json())

app.listen(3000, () => {
    console.log("Listening on port 3000...")
})
//login and register
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

app.post('/registrarUsuario', (req, res) => {
    const codigo = req.body.codigo
    const nombre = req.body.nombre
    const correo = req.body.correo
    const clave = req.body.clave
    const Pseguridad = req.body.Pseguridad
    const Rseguridad = req.body.Rseguridad
    const Rol_id = req.body.Rol_id
    conexion.query(`SELECT * FROM usuarios WHERE correo='${correo}'`,(error,results)=>{
        if(error) throw error
        if(results[0]==undefined){
            conexion.query(`INSERT INTO usuarios (codigo,nombre,correo,clave,Pseguridad,Rseguridad,Rol_id) VALUES('${codigo}',
            '${nombre}','${correo}','${clave}',${Pseguridad},'${Rseguridad}',${Rol_id})`,(error,results)=>{
            if(error)//brb
            {
                console.log(error)
            }else if(results!=null){
                console.log(results)
                res.status(200).send(JSON.stringify(results["affectedRows"]))
            }
            else{
                res.status(404).send()
            }
        })
        }
        else{
            console.log("Duplicated email")
        }
        })
    })
//Users CRUD
app.get('/getUsuario/:correo', (req,res)=>{
    const correo = req.params.correo   
    console.log("Consultando a "+correo)
    conexion.query(`SELECT * FROM usuarios WHERE correo='${correo}'`,(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results[0]))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})

app.get('/getUsuarios', (req,res)=>{
    conexion.query(`SELECT * FROM usuarios`,(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})

app.put('/updateUsuario', (req,res)=>{
    const codigo = req.body.codigo
    const nombre = req.body.nombre
    const correo = req.body.correo
    const clave = req.body.clave
    const Pseguridad = req.body.Pseguridad
    const Rseguridad = req.body.Rseguridad
    const Rol_id = req.body.Rol_id
    console.log("Modificando a :"+codigo)
    conexion.query(`UPDATE usuarios SET nombre='${nombre}', clave='${clave}', correo='${correo}', Pseguridad=${Pseguridad}, Rseguridad='${Rseguridad}', Rol_id=${Rol_id} WHERE codigo=${codigo}`,(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        console.log(results)
        res.status(200).send(JSON.stringify(results))
    }
    else{
        console.log(results)
        res.status(404).send()
    }
    })
})


app.patch('/updateUnUsuario/:palabras', (req,res)=>{
    const palabras = req.params.palabras
    console.log(palabras)
    const words = palabras.split(',')
    const valor = words[0]
    const campo = words[1]
    const codigo = words[2]
    console.log("Modificando a :"+codigo)
    conexion.query(`UPDATE usuarios SET ${campo}='${valor}' WHERE codigo=${codigo}`, ( error,results) =>{
        if(error)
        {
            console.log(error)
        }else if(results!=null){
            console.log(results)
            res.status(200).send(JSON.stringify(results["affectedRows"]))
        }
        else{
            console.log(results)
            res.status(404).send()
        }
    })
})

app.delete('/deleteUsuario/:codigo', (req,res)=>{
    const codigo = req.params.codigo
    console.log(codigo)
    conexion.query('DELETE FROM usuarios WHERE ?',[{codigo}],(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results["affectedRows"]))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})
//Bikes CRUD
app.post('/registrarBicicleta', (req, res) => {
    const idBicicleta = req.body.idBicicleta
    const cedulaPropietario = req.body.cedulaPropietario
    const fechaRegistro = req.body.fechaRegistro
    const lugarRegistro = req.body.lugarRegistro
    const Marca_id = req.body.Marca_id
    const numSerie = req.body.numSerie
    const Tipo_id = req.body.Tipo_id
    const color = req.body.color
    const Estudiante_id = req.body.Estudiante_id

    conexion.query(`INSERT INTO bicicletas (idBicicleta,cedulaPropietario,fechaRegistro,lugarRegistro,Marca_id,numSerie,Tipo_id,color,Estudiante_id) 
    VALUES('${idBicicleta}','${cedulaPropietario}','${fechaRegistro}','${lugarRegistro}',${Marca_id},'${numSerie}',${Tipo_id},'${color}','${Estudiante_id}')`,(error,results)=>{
    if(error)
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

app.get('/getBicicleta/:idBicicleta', (req,res)=>{
    const idBicicleta = req.params.idBicicleta   
    console.log("Consultando a "+idBicicleta)
    conexion.query(`SELECT * FROM bicicletas WHERE idBicicleta='${idBicicleta}'`,(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results[0]))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})

app.get('/getBicicletas', (req,res)=>{
    conexion.query('SELECT * FROM bicicletas',(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results[0]))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})
/* update bicicleta por parte del admin y usuario // no funciona
app.put('/updateBicicleta', (req,res)=>{
    const idBicicleta = req.body.idBicicleta
    const cedulaPropietario = req.body.cedulaPropietario
    const fechaRegistro = req.body.fechaRegistro
    const lugarRegistro = req.body.lugarRegistro
    const Marca_id = req.body.Marca_id
    const numSerie = req.body.numSerie
    const Tipo_id = req.body.Tipo_id
    const color = req.body.color
    conexion.queryconexion.queryconexion.query(`UPDATE bicicletas SET cedulaPropietario='${cedulaPropietario}', fechaRegistro='${fechaRegistro}', 
    lugarRegistro='${lugarRegistro}', Marca_id=${Marca_id}, numSerie='${numSerie}', Tipo_id=${Tipo_id}, color='${color}' WHERE idBicicleta='${idBicicleta}'`,(error,results)=>{
    if(error)
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
app.put('/updateBikeadmin', (req,res)=>{
    const idBicicleta = req.body.idBicicleta
    const cedulaPropietario = req.body.cedulaPropietario
    const fechaRegistro = req.body.fechaRegistro
    const lugarRegistro = req.body.lugarRegistro
    const Marca_id = req.body.Marca_id
    const numSerie = req.body.numSerie
    const Tipo_id = req.body.Tipo_id
    const color = req.body.color
    conexion.queryconexion.queryconexion.query(`UPDATE bicicletas SET idBicicleta=${idBicicleta}, cedulaPropietario='${cedulaPropietario}', fechaRegistro='${fechaRegistro}', 
    lugarRegistro='${lugarRegistro}', Marca_id=${Marca_id}, numSerie='${numSerie}', Tipo_id=${Tipo_id}, color='${color}' WHERE idBicicleta='${Estudiante_id}'`,(error,results)=>{
    if(error)
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
*/

app.delete('/deleteBicicleta/:idBicicleta', (req,res)=>{
    const idBicicleta = req.params.idBicicleta
    console.log(idBicicleta)
    conexion.query('DELETE FROM bicicletas WHERE ?',[{idBicicleta}],(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results["affectedRows"]))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})
/* registro-obtener uno y todos-actualizar y borrar cupos, 
lo tenia antes pero vi que en sus ramas ya lo tienen y escrito en español
no como slots como lo hice, igual aca esta el codigo por si acaso
app.post('/registerSlots', (req, res) => {
    const idCupo = req.body.idCupo
    const seccion = req.body.seccion
    const estado = req.body.estado

    conexion.query(`INSERT INTO cupos (idCupo,seccion,estado) 
    VALUES(${idCupo},'${seccion}',${fechaRegistro},${estado}')`,(error,results)=>{
    if(error)
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

app.get('/getSlots', (req,res)=>{
    conexion.query(`SELECT * FROM cupos`,(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})

app.get('/getSlot/:idCupo', (req,res)=>{
    const idCupo = req.params.idCupo   
    console.log("Consultando a "+idCupo)
    conexion.query(`SELECT * FROM cupos WHERE idCupo='${idCupo}'`,(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results[0]))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})

app.put('/updateSlots', (req,res)=>{
    const idCupo = req.body.idCupo
    const seccion = req.body.seccion
    const estado = req.body.estado

    conexion.queryconexion.queryconexion.query(`UPDATE cupos SET seccion=${seccion}, estado='${estado}' WHERE idCupo='${idCupo}'`,(error,results)=>{
    if(error)
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

app.delete('/deleteSlots/:idCupo', (req,res)=>{
    const idCupo = req.params.idCupo
    console.log(idCupo)
    conexion.query('DELETE FROM cupos WHERE ?',[{idCupo}],(error,results)=>{
    if(error)
    {
        console.log(error)
    }else if(results!=null){
        res.status(200).send(JSON.stringify(results["affectedRows"]))
    }
    else{
        console.log("3")
        console.log(results)
        res.status(404).send()
    }
    })
})
*/
//registerBrands
//getBrands
//updateBrands
//deleteBrands

//registerParkinglot
//getParkinglot
//updateParkinglots
//deleteParkinglot

//registerQuestions
//getQuestions
//updateQuestions
//deleteQuestions

//registerRoles
//getRoles
//updateRoles
//deleteRoles

//registerTypes
//getTypes
//updateTypes
//deleteTypes
