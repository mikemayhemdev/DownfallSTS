package sneckomod.cards;

import basemod.helpers.BaseModCardTags;
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
import sneckomod.powers.SerpentMindPower;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;

public class SerpentMind extends AbstractSneckoCard implements OnObtainCard {

    public final static String ID = makeID("SerpentMind");

    //stupid intellij stuff POWER, SELF, RARE

    private static final int MAGIC = 1;
    private static int SOFTLOCK = 0;

    public SerpentMind() {
        super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        tags.add(BaseModCardTags.FORM);
        isEthereal = true;
        SneckoMod.loadJokeCardImage(this, "SerpentMind.png");
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
        return false;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        applyToSelf(new SerpentMindPower(magicNumber));
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.RARE);

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(newCard);
            }

            if (newCard != null && !cardListDuplicate(cardsToReward, newCard)) {
                SOFTLOCK = 0;
                AbstractCard cardCopy = newCard.makeCopy();
                cardsToReward.add(cardCopy);
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
