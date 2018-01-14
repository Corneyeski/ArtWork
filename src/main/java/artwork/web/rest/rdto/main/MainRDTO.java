package artwork.web.rest.rdto.main;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainRDTO {

    private Set<MultimediaRDTO> multimedia = new HashSet<>();
    private Set<OfferRDTO> offers = new HashSet<>();

    public Set<MultimediaRDTO> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Set<MultimediaRDTO> multimedia) {
        this.multimedia = multimedia;
    }

    public Set<OfferRDTO> getOffers() {
        return offers;
    }

    public void setOffers(Set<OfferRDTO> offers) {
        this.offers = offers;
    }
}
