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
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import hermit.cards.HighCaliber;
import slimebound.cards.TongueLash;
import sneckomod.SneckoMod;
import sneckomod.powers.AceOfWandsPower;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class AceOfWands extends AbstractSneckoCard {

    public final static String ID = makeID("AceOfWands");

    //stupid intellij stuff POWER, SELF, RARE

    private static int SOFTLOCK = 0;
    //Queen of Pentacles

    public AceOfWands() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
        isEthereal = true;
        SneckoMod.loadJokeCardImage(this, "AceOfWands.png");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID) && (SOFTLOCK < 100)) {
                SOFTLOCK++;
                return true;
            }
        }
        if (SOFTLOCK >= 100) {
            System.out.println("SOFTLOCK DETECTED!!!");
        }
        SOFTLOCK = 0;
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new AceOfWandsPower(this.magicNumber));
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicateDebuff(c -> (!c.hasTag(SneckoMod.BANNEDFORSNECKO)));
            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(newCard);
            }

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
