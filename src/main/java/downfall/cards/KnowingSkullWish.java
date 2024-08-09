package downfall.cards;

import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.ObtainPotionAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.OctoChoiceAction;
import downfall.downfallMod;
import downfall.util.OctopusCard;
import expansioncontent.expansionContentMod;
import expansioncontent.cards.AbstractExpansionCard;
import sneckomod.actions.ChangeGoldAction;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;

public class KnowingSkullWish extends AbstractExpansionCard implements OctopusCard {

    public final static String ID = makeID("KnowingSkullWish");
    public static final String[] NAMES = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("OctoChoiceCards")).NAMES;
    public static final String[] TEXT = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("OctoChoiceCards")).TEXT;

    public KnowingSkullWish() {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF, CardColor.COLORLESS);
        isEthereal = true;
        exhaust = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new OctoChoiceAction(m, this));
    }

    public ArrayList<OctoChoiceCard> choiceList() {
        ArrayList<OctoChoiceCard> cardList = new ArrayList<>();
        OctoChoiceCard goldCard, colorlessCard, potionCard;
        if (!upgraded) {
            goldCard = new OctoChoiceCard("ks:0", NAMES[0], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[0]);
            colorlessCard = new OctoChoiceCard("ks:1", NAMES[1], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[1]);
            potionCard = new OctoChoiceCard("ks:2", NAMES[2], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[2]);
        }
        else {
            goldCard = new OctoChoiceCard("ks:0", NAMES[0], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[24]);
            colorlessCard = new OctoChoiceCard("ks:1", NAMES[1], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[25]);
            potionCard = new OctoChoiceCard("ks:2", NAMES[2], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[26]);
            goldCard.upgrade();
            colorlessCard.upgrade();
            potionCard.upgrade();
        }
        cardList.add(goldCard);
        cardList.add(colorlessCard);
        cardList.add(potionCard);
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "ks:0": {
                int gold = upgraded ? 20 : 15;
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 3));
                atb(new ChangeGoldAction(gold));
                break;
            }
            case "ks:1": {
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 3));
                ArrayList<AbstractCard> list = new ArrayList<>(AbstractDungeon.colorlessCardPool.group);
                AbstractCard q = list.get(cardRandomRng.random(list.size() - 1));
                if (upgraded)
                    q.upgrade();
                atb(new MakeTempCardInHandAction(q));
                break;
            }
            case "ks:2": { //faster search     "Obtain Guardian *Boss cards."        获得守护者 *首领 牌。
                int damage = upgraded ? 5 : 7;
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, damage));
                atb(new ObtainPotionAction(PotionHelper.getRandomPotion()));
                break;
            }
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }
}
