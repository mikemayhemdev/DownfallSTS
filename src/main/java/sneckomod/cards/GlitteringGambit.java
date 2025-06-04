package sneckomod.cards;

import com.evacipated.cardcrawl.mod.stslib.fields.cards.AbstractCard.SoulboundField;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.NecronomicurseEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import expansioncontent.expansionContentMod;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class GlitteringGambit extends AbstractSneckoCard {
    public final static String ID = makeID("GlitteringGambit");

    private static final int MAGIC = 150;
    private static int SOFTLOCK = 0;

    public GlitteringGambit() {
        super(ID, -2, CardType.POWER, CardRarity.RARE, CardTarget.SELF);
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = false;
        tags.add(CardTags.HEALING);
        tags.add(expansionContentMod.UNPLAYABLE);
        SneckoMod.loadJokeCardImage(this, "GlitteringGambit.png");
        SoulboundField.soulbound.set(this, true);
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        for (AbstractCard existingCard : cardsList) {
            if (existingCard.cardID.equals(card.cardID) && (SOFTLOCK < 100)) {
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
    }

    @Override
    public void onObtainCard() {
        AbstractDungeon.player.gainGold(this.magicNumber);
        for (int i = 0; i < 1; i++)
            displayCardRewards(AbstractCard.CardRarity.RARE, "Special Bonus Card!");
    }

    private void displayCardRewards(AbstractCard.CardRarity rarity, String rewardText) {
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();

        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> c.rarity == rarity);
            if (newCard != null && !cardListDuplicate(cardsToReward, newCard)) {
                SOFTLOCK = 0;
                AbstractCard cardCopy = newCard.makeCopy();
                cardCopy.upgrade();
                cardsToReward.add(cardCopy);
            }
        }

        SneckoMod.addGift(cardsToReward);
    }

    @Override
    public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        return false;
    }

    public void onRemoveFromMasterDeck() {
        AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(new CurseOfTheBell(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            isEthereal = true;
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}
