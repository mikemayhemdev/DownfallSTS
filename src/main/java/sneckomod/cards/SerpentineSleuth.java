package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.cards.interfaces.OnObtainCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import sneckomod.SneckoMod;
import sneckomod.powers.SerpentineSleuthPower;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class SerpentineSleuth extends AbstractSneckoCard implements OnObtainCard {

    public final static String ID = makeID("SerpentineSleuth");

    //stupid intellij stuff POWER, SELF, RARE
    private static int SOFTLOCK = 0;

    public SerpentineSleuth() {
        super(ID, 4, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        //baseMagicNumber = magicNumber = 1;
        isEthereal = true;
        SneckoMod.loadJokeCardImage(this, "SerpentineSleuth.png");
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
        applyToSelf(new SerpentineSleuthPower(1));
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.type == AbstractCard.CardType.POWER && c.rarity == AbstractCard.CardRarity.RARE);

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
            upgradeBaseCost(3);
        }
    }
}