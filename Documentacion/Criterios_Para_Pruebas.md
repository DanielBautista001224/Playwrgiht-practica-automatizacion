Para los test se realizaron 5, cada uno de estos busca poner a prueba Un problema que 
podria generarse al trabajar con este formulario, como lo son:


Test 1: Parametros aleatorios


Este utiliza la forma que se consideraria adecuada para llenar el formulario


Test 2: fecha de nacimiento en blanco


Esta prueba es bastante interesante, ya que con lo obtenido en el analisis se esperaria que al no haber 
fecha de nacimiento o esta estar vacia se deje el espacio sin datos, y se elimine el contenido de la pagina.


En lugar de ello el formato toma el dia como si fuese el dia en el que se ejecutó la prueba, evitando 
asi que se elimine el contenido


Test 4: Sin Hobbies


Esta prueba esta hecha para mostrar el comportamiento del programa al no seleccionar ningun hobbie, 
este no genera ningun problema y llena el formulario con normalidad


Test 5: Fecha Limite Inferior


Esta prueba tiene como objetivo poner a prueba la fecha minima que permite el formato, a pesar de tener la
fecha de nacimiento de hace mas de 126 años permite diligenciar y enviar el formato sin ningun problema

