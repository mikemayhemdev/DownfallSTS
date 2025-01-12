package sneckomod.cards;

import automaton.cards.Undervolt;
import collector.cards.Billow;
import collector.cards.CursedWail;
import collector.cards.DarkLordForm;
import collector.cards.IllTakeThat;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.green.Choke;
import com.megacrit.cardcrawl.cards.green.PiercingWail;
import com.megacrit.cardcrawl.cards.purple.TalkToTheHand;
import com.megacrit.cardcrawl.cards.red.Disarm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hermit.cards.HighCaliber;
import sneckomod.SneckoMod;
import sneckomod.powers.AceOfWandsPower;

import java.util.ArrayList;

public class AceOfWands extends AbstractSneckoCard {

    public final static String ID = makeID("AceOfWands");

    //stupid intellij stuff POWER, SELF, RARE


    //Queen of Pentacles

    public AceOfWands() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        isEthereal = true;
        SneckoMod.loadJokeCardImage(this, "AceOfWands.png");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                return true;
            }
        }
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AceOfWandsPower(this.magicNumber));
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> (!c.cardID.equals(IllTakeThat.ID) && (c.rawDescription.contains("Apply") || c.rawDescription.contains("apply") || c.rawDescription.contains("applies") || c.rawDescription.contains("Lick") || c.rawDescription.contains("Debuff") || c.rawDescription.contains("Steal") || c.cardID.equals(Disarm.ID) || c.cardID.equals(Choke.ID) || c.cardID.equals(TalkToTheHand.ID) || c.cardID.equals(PiercingWail.ID) || c.cardID.equals(DarkLordForm.ID) || c.cardID.equals(CursedWail.ID) || c.cardID.equals(Undervolt.ID) || c.cardID.equals(Billow.ID))));
            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
            }
        }

        SneckoMod.addGift(cardsToReward);
        ;
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = false;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
