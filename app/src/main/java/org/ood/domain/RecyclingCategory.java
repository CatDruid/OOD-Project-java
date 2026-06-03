package org.ood.domain;

/**
 * Represents a recycling category for each material.
 * Each is then paired with a readable {@link #guidance} on how to recycle it.
 */
public enum RecyclingCategory {
        Plastic("Rinse and flatten plastic packaging and deposit at your nearest recycling station (återvinningsstation)."),
        Paper("Clean and flatten paper packaging, such as cardboard and paper bags, and deposit at your nearest recycling station."),
        Metal("Rinse metal packaging such as tins and cans and deposit at your nearest recycling station."),
        Clear_Glass("Rinse clear glass packaging and deposit at your nearest recycling station, without lids."),
        Coloured_Glass("Rinse coloured glass packaging and deposit at your nearest recycling station, without lids."),
        Newspapers("Deposit newspapers, magazines, and printed paper at your nearest recycling station."),
        Residual("Dispose of waste that cannot be sorted into any other category in the residual waste bin (restavfall)."),
        Food("Dispose of all food scraps in the food waste bin (matavfall) to be composted or converted to biogas."),
        Hazardous("Deliver hazardous waste such as batteries, chemicals, and electronics to your nearest recycling centre (återvinningscentral)."),
        Pant("Return cans and bottles marked with 'pant' to a Pantamera machine at your nearest store for your deposit refund.");

        /**
         * Disposal guidance for each category.
         * Intended to display for end-users.
         */
        public final String guidance;

        /**
         * Constructs a {@code RecyclingCategory} with provided guidance.
         * @param guidance      A readable description of how to dispose of the material.
         */
        RecyclingCategory(String guidance){
            this.guidance = guidance;
        }
    }
