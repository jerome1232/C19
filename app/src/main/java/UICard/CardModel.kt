package UICard

/**
 * The model for the card layout
 *
 * @author Chase Moses
 */
class CardModel(val title : String, val numTotal : Int, val numActive : Int, val numRecover : Int, val numDeaths : Int ) {

    private val cardTitle : String = title
    private val totalCaseNum : Int = numTotal
    private val activeCaseNum : Int = numActive
    private val recoveredNum : Int = numRecover
    private val deathsNum : Int = numDeaths


    


}