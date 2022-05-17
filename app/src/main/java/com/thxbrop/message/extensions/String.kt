package com.thxbrop.message.extensions

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

data class PhoneNumber(
    val value: String
) {
    val code
        get() = with(value.trim()) {
            if (startsWith('+')) substring(1, length - 11).toLong()
            else substring(0, length - 11).toLong()
        }

    val number
        get() = with(value.trim()) { takeLast(11).toLong() }

    val formatted
        get() = buildString {
            append('+')
            append(code)
            append(' ')
            val number = number.toString()
            append(number.take(3))
            append('-')
            append(number.subSequence(3, 7))
            append('-')
            append(number.takeLast(4))
        }
    val countryPrefixOrNull
        get() = run {
            if (countryPrefixList == null) {
                val type = object : TypeToken<List<CountryPrefix>>() {}.type
                countryPrefixList = Gson().fromJson<List<CountryPrefix>>(json, type)
            }
            countryPrefixList?.firstOrNull { it.prefix == code }
        }

    val countryPrefix
        @Throws(PrefixNotExistException::class)
        get() = countryPrefixOrNull
            ?: throw PrefixNotExistException("The code: $code cannot be converted to country code.")
}

private data class PhoneNumberFormatException(override val message: String) : Exception(message)

private data class PrefixNotExistException(override val message: String) : Exception(message)

@Throws(PhoneNumberFormatException::class)
fun String.asPhoneNumber() = run s@{
    (if (!startsWith('+') && length < 13)
        buildString builder@{
            append(this@s)
            repeat(13 - length) {
                append('0')
            }
        }
    else if (startsWith('+') && length < 14) {
        buildString builder@{
            append(this@s)
            repeat(14 - length) {
                append('0')
            }
        }
    } else this).let(::PhoneNumber)
}

fun main() {
    println("+".asPhoneNumber().code)
}

data class CountryPrefix(val prefix: Long, val c: String)

private var countryPrefixList: List<CountryPrefix>? = null

private const val json = """
    [
        { prefix: '1', c: 'USA' },
        { prefix: '1', c: 'PuertoRico' },
        { prefix: '1', c: 'Canada' },
        { prefix: '7', c: 'Russia' },
        { prefix: '7', c: 'Kazeakhstan' },
        { prefix: '20', c: 'Egypt' },
        { prefix: '27', c: 'South Africa' },
        { prefix: '30', c: 'Greece' },
        { prefix: '31', c: 'Netherlands' },
        { prefix: '32', c: 'Belgium' },
        { prefix: '33', c: 'France' },
        { prefix: '34', c: 'Spain' },
        { prefix: '36', c: 'Hungary' },
        { prefix: '40', c: 'Romania' },
        { prefix: '41', c: 'Switzerland' },
        { prefix: '43', c: 'Austria' },
        { prefix: '44', c: 'United Kingdom' },
        { prefix: '44', c: 'Jersey' },
        { prefix: '44', c: 'Isle of Man' },
        { prefix: '44', c: 'Guernsey' },
        { prefix: '45', c: 'Denmark' },
        { prefix: '46', c: 'Sweden' },
        { prefix: '47', c: 'Norway' },
        { prefix: '48', c: 'Poland' },
        { prefix: '51', c: 'Peru' },
        { prefix: '52', c: 'Mexico' },
        { prefix: '53', c: 'Cuba' },
        { prefix: '54', c: 'Argentina' },
        { prefix: '55', c: 'Brazill' },
        { prefix: '56', c: 'Chile' },
        { prefix: '57', c: 'Colombia' },
        { prefix: '58', c: 'Venezuela' },
        { prefix: '60', c: 'Malaysia' },
        { prefix: '61', c: 'Australia' },
        { prefix: '62', c: 'Indonesia' },
        { prefix: '63', c: 'Philippines' },
        { prefix: '64', c: 'NewZealand' },
        { prefix: '65', c: 'Singapore' },
        { prefix: '66', c: 'Thailand' },
        { prefix: '81', c: 'Japan' },
        { prefix: '82', c: 'Korea' },
        { prefix: '84', c: 'Vietnam' },
        { prefix: '86', c: 'China' },
        { prefix: '90', c: 'Turkey' },
        { prefix: '91', c: 'Indea' },
        { prefix: '92', c: 'Pakistan' },
        { prefix: '93', c: 'Italy' },
        { prefix: '93', c: 'Afghanistan' },
        { prefix: '94', c: 'SriLanka' },
        { prefix: '94', c: 'Germany' },
        { prefix: '95', c: 'Myanmar' },
        { prefix: '98', c: 'Iran' },
        { prefix: '212', c: 'Morocco' },
        { prefix: '213', c: 'Algera' },
        { prefix: '216', c: 'Tunisia' },
        { prefix: '218', c: 'Libya' },
        { prefix: '220', c: 'Gambia' },
        { prefix: '221', c: 'Senegal' },
        { prefix: '222', c: 'Mauritania' },
        { prefix: '223', c: 'Mali' },
        { prefix: '224', c: 'Guinea' },
        { prefix: '225', c: 'Cote divoire' },
        { prefix: '226', c: 'Burkina Faso' },
        { prefix: '227', c: 'Niger' },
        { prefix: '228', c: 'Togo' },
        { prefix: '229', c: 'Benin' },
        { prefix: '230', c: 'Mauritius' },
        { prefix: '231', c: 'Liberia' },
        { prefix: '232', c: 'Sierra Leone' },
        { prefix: '233', c: 'Ghana' },
        { prefix: '234', c: 'Nigeria' },
        { prefix: '235', c: 'Chad' },
        { prefix: '236', c: 'Central African Republic' },
        { prefix: '237', c: 'Cameroon' },
        { prefix: '238', c: 'Cape Verde' },
        { prefix: '239', c: 'Sao Tome and Principe' },
        { prefix: '240', c: 'Guinea' },
        { prefix: '241', c: 'Gabon' },
        { prefix: '242', c: 'Republic of the Congo' },
        { prefix: '243', c: 'Democratic Republic of the Congo' },
        { prefix: '244', c: 'Angola' },
        { prefix: '247', c: 'Ascension' },
        { prefix: '248', c: 'Seychelles' },
        { prefix: '249', c: 'Sudan' },
        { prefix: '250', c: 'Rwanda' },
        { prefix: '251', c: 'Ethiopia' },
        { prefix: '253', c: 'Djibouti' },
        { prefix: '254', c: 'Kenya' },
        { prefix: '255', c: 'Tanzania' },
        { prefix: '256', c: 'Uganda' },
        { prefix: '257', c: 'Burundi' },
        { prefix: '258', c: 'Mozambique' },
        { prefix: '260', c: 'Zambia' },
        { prefix: '261', c: 'Madagascar' },
        { prefix: '262', c: 'Reunion' },
        { prefix: '262', c: 'Mayotte' },
        { prefix: '263', c: 'Zimbabwe' },
        { prefix: '264', c: 'Namibia' },
        { prefix: '265', c: 'Malawi' },
        { prefix: '266', c: 'Lesotho' },
        { prefix: '267', c: 'Botwana' },
        { prefix: '268', c: 'Swaziland' },
        { prefix: '269', c: 'Comoros' },
        { prefix: '297', c: 'Aruba' },
        { prefix: '298', c: 'Faroe Islands' },
        { prefix: '299', c: 'Greenland' },
        { prefix: '350', c: 'Gibraltar' },
        { prefix: '351', c: 'Portugal' },
        { prefix: '352', c: 'Luxembourg' },
        { prefix: '353', c: 'Ireland' },
        { prefix: '354', c: 'Iceland' },
        { prefix: '355', c: 'Albania' },
        { prefix: '356', c: 'Malta' },
        { prefix: '357', c: 'Cyprus' },
        { prefix: '358', c: 'Finland' },
        { prefix: '359', c: 'Bulgaria' },
        { prefix: '370', c: 'Lithuania' },
        { prefix: '371', c: 'Latvia' },
        { prefix: '372', c: 'Estonia' },
        { prefix: '373', c: 'Moldova' },
        { prefix: '374', c: 'Armenia' },
        { prefix: '375', c: 'Belarus' },
        { prefix: '376', c: 'Andorra' },
        { prefix: '377', c: 'Monaco' },
        { prefix: '378', c: 'San Marino' },
        { prefix: '380', c: 'Ukraine' },
        { prefix: '381', c: 'Serbia' },
        { prefix: '382', c: 'Montenegro' },
        { prefix: '383', c: 'Kosovo' },
        { prefix: '385', c: 'Croatia' },
        { prefix: '386', c: 'Slovenia' },
        { prefix: '387', c: 'Bosnia and Herzegovina' },
        { prefix: '389', c: 'Macedonia' },
        { prefix: '420', c: 'Czech Republic' },
        { prefix: '421', c: 'Slovakia' },
        { prefix: '423', c: 'Liechtenstein' },
        { prefix: '501', c: 'Belize' },
        { prefix: '502', c: 'Guatemala' },
        { prefix: '503', c: 'EISalvador' },
        { prefix: '504', c: 'Honduras' },
        { prefix: '505', c: 'Nicaragua' },
        { prefix: '506', c: 'Costa Rica' },
        { prefix: '507', c: 'Panama' },
        { prefix: '509', c: 'Haiti' },
        { prefix: '590', c: 'Guadeloupe' },
        { prefix: '591', c: 'Bolivia' },
        { prefix: '592', c: 'Guyana' },
        { prefix: '593', c: 'Ecuador' },
        { prefix: '594', c: 'French Guiana' },
        { prefix: '595', c: 'Paraguay' },
        { prefix: '596', c: 'Martinique' },
        { prefix: '597', c: 'Suriname' },
        { prefix: '598', c: 'Uruguay' },
        { prefix: '599', c: 'Netherlands Antillse' },
        { prefix: '670', c: 'Timor Leste' },
        { prefix: '673', c: 'Brunei' },
        { prefix: '675', c: 'Papua New Guinea' },
        { prefix: '676', c: 'Tonga' },
        { prefix: '678', c: 'Vanuatu' },
        { prefix: '679', c: 'Fiji' },
        { prefix: '682', c: 'Cook Islands' },
        { prefix: '684', c: 'Samoa Eastern' },
        { prefix: '685', c: 'Samoa Western' },
        { prefix: '687', c: 'New Caledonia' },
        { prefix: '689', c: 'French Polynesia' },
        { prefix: '852', c: 'Hong Kong' },
        { prefix: '853', c: 'Macao' },
        { prefix: '855', c: 'Cambodia' },
        { prefix: '856', c: 'Laos' },
        { prefix: '880', c: 'Bangladesh' },
        { prefix: '886', c: 'Taiwan' },
        { prefix: '960', c: 'Maldives' },
        { prefix: '961', c: 'Lebanon' },
        { prefix: '962', c: 'Jordan' },
        { prefix: '963', c: 'Syria' },
        { prefix: '964', c: 'Iraq' },
        { prefix: '965', c: 'Kuwait' },
        { prefix: '966', c: 'Saudi Arabia' },
        { prefix: '967', c: 'Yemen' },
        { prefix: '968', c: 'Oman' },
        { prefix: '970', c: 'Palestinian' },
        { prefix: '971', c: 'United Arab Emirates' },
        { prefix: '972', c: 'Israel' },
        { prefix: '973', c: 'Bahrain' },
        { prefix: '974', c: 'Qotar' },
        { prefix: '975', c: 'Bhutan' },
        { prefix: '976', c: 'Mongolia' },
        { prefix: '977', c: 'Nepal' },
        { prefix: '992', c: 'Tajikistan' },
        { prefix: '993', c: 'Turkmenistan' },
        { prefix: '994', c: 'Azerbaijan' },
        { prefix: '995', c: 'Georgia' },
        { prefix: '996', c: 'Kyrgyzstan' },
        { prefix: '998', c: 'Uzbekistan' },
        { prefix: '1242', c: 'Bahamas' },
        { prefix: '1246', c: 'Barbados' },
        { prefix: '1264', c: 'Anguilla' },
        { prefix: '1268', c: 'Antigua and Barbuda' },
        { prefix: '1340', c: 'Virgin Islands' },
        { prefix: '1345', c: 'Cayman Islands' },
        { prefix: '1441', c: 'Bermuda' },
        { prefix: '1473', c: 'Grenada' },
        { prefix: '1649', c: 'Turks and Caicos Islands' },
        { prefix: '1664', c: 'Montserrat' },
        { prefix: '1671', c: 'Guam' },
        { prefix: '1758', c: 'St.Lucia' },
        { prefix: '1767', c: 'Dominica' },
        { prefix: '1784', c: 'St.Vincent' },
        { prefix: '1809', c: 'Dominican Republic' },
        { prefix: '1868', c: 'Trinidad and Tobago' },
        { prefix: '1869', c: 'St Kitts and Nevis' },
        { prefix: '1876', c: 'Jamaica' }
    ]
"""