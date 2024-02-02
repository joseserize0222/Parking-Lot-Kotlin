package parking

class Car(val id : String, val color : String){

    override fun toString() : String{
        return "Id: $id Color: $color"
    }
    override fun equals(other : Any?) : Boolean{
        return if(other is Car){
            other.id == this.id
        } else false
    }
}

class Parking(numberOfPlaces : Int){
    private val carList : MutableList<Car?>
    private var occupied : Int = 0
    init{
        carList = MutableList<Car?>(numberOfPlaces) {null}
    }
    fun check(place : Int) : Boolean{
        return (carList[place-1] == null)
    }
    fun park(car : Car) : Int{
        val place = carList.indexOf(null)
        occupied++
        return if(place == -1) 0 else{
            carList[place] = car
            place+1
        }
    }

    fun regByColor(color: String){
        var answer = "No cars with color $color were found."
        var first = true
        for(i in 0 until carList.size){
            if(carList[i] is Car){
                if(carList[i]?.color?.uppercase() == color.uppercase()){
                    if(first){
                        answer = carList[i]?.id.toString()
                        first = false
                    }
                    else{
                        answer = answer + ", " + carList[i]?.id.toString()
                    }
                }
            }
        }
        println(answer)
    }

    fun spotByColor(color: String){
        var answer = "No cars with color $color were found."
        var first = true
        for(i in 0 until carList.size){
            if(carList[i] is Car){
                if(carList[i]?.color?.uppercase() == color.uppercase()){
                    if(first){
                        answer = (i+1).toString()
                        first = false
                    }
                    else{
                        answer = answer + ", " + (i+1).toString()
                    }
                }
            }
        }
        println(answer)
    }

    fun spotByRegister(id: String){
        var answer = "No cars with registration number $id were found."
        for(i in 0 until carList.size){
            if(carList[i] is Car){
                if(carList[i]?.id == id){
                    answer = (i+1).toString()
                    break
                }
            }
        }
        println(answer)
    }
    fun display(){
        if(occupied == 0) println("Parking lot is empty.")
        else{
            for(i in 0 until carList.size){
                if(carList[i] is Car){
                    println("${i+1} ${carList[i]?.id} ${carList[i]?.color}")
                }
            }
        }
    }

    fun unPark(place : Int){
        occupied--
        carList[place-1] = null
    }
}

fun main() {
    var parking : Parking? = null

    while(true) {
        val commandList: List<String> = readln().split(" ")
        when(commandList.first()){
            "create"->{
                parking = Parking(commandList[1].toInt())
                println("Created a parking lot with ${commandList[1]} spots.")
            }
            "leave"-> {
                if(parking == null){
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                println(
                    if (!parking.check(commandList[1].toInt())) {
                        parking.unPark(commandList[1].toInt())
                        "Spot ${commandList[1]} is free."
                    } else {
                        "There is no car in spot ${commandList[1]}."
                    }
                )
            }
            "park" ->{
                if(parking == null){
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                val auxCar = Car(
                    commandList[1],
                    commandList[2].replaceFirstChar { if (it.isUpperCase()) it.titlecase() else it.toString() })
                val spot = parking.park(auxCar)
                println(if (spot == 0) "Sorry, the parking lot is full." else "${auxCar.color} car parked in spot $spot.")
            }
            "status"->{
                if(parking == null){
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                parking.display()
            }
            "spot_by_color"->{
                if(parking == null){
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                parking.spotByColor(commandList[1])
            }
            "reg_by_color"->{
                if(parking == null){
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                parking.regByColor(commandList[1])
            }
            "spot_by_reg"->{
                if(parking == null){
                    println("Sorry, a parking lot has not been created.")
                    continue
                }
                parking.spotByRegister(commandList[1])
            }
            "exit"->break
        }
    }
}
