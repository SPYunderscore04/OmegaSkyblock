package com.github.spyunderscore04.omegaskyblock.gamemodel.skyblock

data class TabListData(
    var accountInfo: AccountInfo? = null
)

data class AccountInfo(
    var profileName: String? = null,
    var petSitter: String? = null,
    var bankString: String? = null,
    var interest: String? = null
) {
    companion object {
        fun fromTabListLines(lines: List<String>): AccountInfo {
            val profileName = lines.getOrNull(0)?.substringAfter("Profile: ")
            val petSitter = lines.getOrNull(1)?.substringAfter("Pet Sitter: ")
            val bankString = lines.getOrNull(2)?.substringAfter("Bank: ")
            val interest = lines.getOrNull(3)?.substringAfter("Interest: ")

            return AccountInfo(profileName, petSitter, bankString, interest)
        }
    }
}
