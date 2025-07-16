package sneckomod.cards;

import basemod.BaseMod;
import com.evacipated.cardcrawl.mod.stslib.actions.common.FetchAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.FrozenEgg2;
import com.megacrit.cardcrawl.relics.MoltenEgg2;
import com.megacrit.cardcrawl.relics.ToxicEgg2;
import hermit.relics.Spyglass;
import hermit.util.Wiz;
import sneckomod.SneckoMod;
import sneckomod.actions.DrawOffclassAction;
import sneckomod.relics.UnknownEgg;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class BeyondArmor extends AbstractSneckoCard {

    public final static String ID = makeID("BeyondArmor");

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 2;
    private static int SOFTLOCK = 0;

    public BeyondArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;
        SneckoMod.loadJokeCardImage(this, "BeyondArmor.png");
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

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new GainBlockAction(p, p, this.block));
        this.addToBot(new DrawOffclassAction(magicNumber));
    }

    @Override
    public void onObtainCard() {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == AbstractCard.CardRarity.COMMON);

            for (AbstractRelic r : AbstractDungeon.player.relics) {
                r.onPreviewObtainCard(newCard);
            }

            if (!cardListDuplicate(cardsToReward, newCard)) {
                SOFTLOCK = 0;
                cardsToReward.add(newCard.makeCopy());
            }
        }
        SneckoMod.addGift(cardsToReward);
        ;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}
