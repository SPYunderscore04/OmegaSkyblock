package com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock

data class TabListData(
    var accountInfo: AccountInfo? = null
)

data class AccountInfo(
    var profileName: String,
    var petSitter: String,
    var bankString: String,
    var interest: String
) {
    companion object {
        fun fromTabListLines(lines: List<String>): AccountInfo {
            require(lines.size == 4)

            val profileName = lines[0].substringAfter("Profile: ")
            val petSitter = lines[1].substringAfter(" Pet Sitter: ")
            val bankString = lines[2].substringAfter(" Bank: ")
            val interest = lines[3].substringAfter(" Interest: ")

            return AccountInfo(profileName, petSitter, bankString, interest)
        }
    }
}
