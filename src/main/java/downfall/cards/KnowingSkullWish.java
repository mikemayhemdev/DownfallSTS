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
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import sneckomod.actions.ChangeGoldAction;

import java.util.ArrayList;

public class KnowingSkullWish extends AbstractExpansionCard implements OctopusCard {

    public final static String ID = makeID("KnowingSkullWish");
    public static final String[] NAMES = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("OctoChoiceCards")).NAMES;
    public static final String[] TEXT = CardCrawlGame.languagePack.getCharacterString(downfallMod.makeID("OctoChoiceCards")).TEXT;

    //stupid intellij stuff SKILL, SELF, RARE

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
        cardList.add(new OctoChoiceCard("ks:0", NAMES[0], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[0]));
        cardList.add(new OctoChoiceCard("ks:1", NAMES[1], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[1]));
        cardList.add(new OctoChoiceCard("ks:2", NAMES[2], expansionContentMod.makeCardPath("KnowingSkullWish.png"), TEXT[2]));
        return cardList;
    }

    public void doChoiceStuff(AbstractMonster m, OctoChoiceCard card) {
        switch (card.cardID) {
            case "ks:0": {
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 3));
                atb(new ChangeGoldAction(40));
                break;
            }
            case "ks:1": {
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 1));
                AbstractCard q = AbstractDungeon.returnColorlessCard();
                atb(new MakeTempCardInHandAction(q));
                break;
            }
            case "ks:2": {
                atb(new LoseHPAction(AbstractDungeon.player, AbstractDungeon.player, 5));
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