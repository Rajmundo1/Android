package hu.bme.aut.android.cryptox.model


class Coin {
    lateinit var id: String
    lateinit var name: String
    lateinit var symbol: String
    var rank: Int = 0
    var price_usd: Double = 0.0
    var price_btc: Double = 0.0
    var volume_usd_24h: Double = 0.0
    var market_cap_usd: Double = 0.0
    var available_supply: Double = 0.0
    var total_supply: Double = 0.0
    var max_supply: Double = 0.0
    var percent_change_1h: Double = 0.0
    var percent_change_24h: Double = 0.0
    var percent_change_7d: Double = 0.0
    var last_updated: Double = 0.0

    var favorite: Int = 0


    constructor(
        id: String,
        name: String,
        symbol: String,
        rank: Int,
        price_usd: Double,
        price_btc: Double,
        volume_usd_24h: Double,
        market_cap_usd: Double,
        available_supply: Double,
        total_supply: Double,
        max_supply: Double,
        percent_change_1h: Double,
        percent_change_24h: Double,
        percent_change_7d: Double,
        last_updated: Double
    ) {
        this.id = id
        this.name = name
        this.symbol = symbol.toLowerCase()
        this.rank = rank
        this.price_usd = price_usd
        this.price_btc = price_btc
        this.volume_usd_24h = volume_usd_24h
        this.market_cap_usd = market_cap_usd
        this.available_supply = available_supply
        this.total_supply = total_supply
        this.max_supply = max_supply
        this.percent_change_1h = percent_change_1h
        this.percent_change_24h = percent_change_24h
        this.percent_change_7d = percent_change_7d
        this.last_updated = last_updated
    }

    constructor()

    constructor(c:Coin){
        this.id = c.id
        this.name = c.name
        this.symbol = c.symbol.toLowerCase()
        this.rank = c.rank
        this.price_usd =c. price_usd
        this.price_btc = c.price_btc
        this.volume_usd_24h = c.volume_usd_24h
        this.market_cap_usd = c.market_cap_usd
        this.available_supply = c.available_supply
        this.total_supply = c.total_supply
        this.max_supply = c.max_supply
        this.percent_change_1h = c.percent_change_1h
        this.percent_change_24h = c.percent_change_24h
        this.percent_change_7d = c.percent_change_7d
        this.last_updated = c.last_updated
        this.favorite = c.favorite
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Coin) return false

        if (id != other.id) return false
        if (name != other.name) return false
        if (symbol != other.symbol) return false
        if (rank != other.rank) return false
        if (price_usd != other.price_usd) return false
        if (price_btc != other.price_btc) return false
        if (volume_usd_24h != other.volume_usd_24h) return false
        if (market_cap_usd != other.market_cap_usd) return false
        if (available_supply != other.available_supply) return false
        if (total_supply != other.total_supply) return false
        if (max_supply != other.max_supply) return false
        if (percent_change_1h != other.percent_change_1h) return false
        if (percent_change_24h != other.percent_change_24h) return false
        if (percent_change_7d != other.percent_change_7d) return false
        if (last_updated != other.last_updated) return false
        if (favorite != other.favorite) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + symbol.hashCode()
        result = 31 * result + rank
        result = 31 * result + price_usd.hashCode()
        result = 31 * result + price_btc.hashCode()
        result = 31 * result + volume_usd_24h.hashCode()
        result = 31 * result + market_cap_usd.hashCode()
        result = 31 * result + available_supply.hashCode()
        result = 31 * result + total_supply.hashCode()
        result = 31 * result + max_supply.hashCode()
        result = 31 * result + percent_change_1h.hashCode()
        result = 31 * result + percent_change_24h.hashCode()
        result = 31 * result + percent_change_7d.hashCode()
        result = 31 * result + last_updated.hashCode()
        result = 31 * result + favorite
        return result
    }


}

