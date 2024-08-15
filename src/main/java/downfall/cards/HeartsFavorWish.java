package downfall.cards;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.purple.DevaForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PotionHelper;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.actions.FlexibleDiscoveryAction;
import downfall.actions.OctoChoiceAction;
import downfall.cards.curses.*;
import downfall.downfallMod;
import downfall.util.OctopusCard;
import expansioncontent.cards.AbstractDownfallCard;
import expansioncontent.cards.AbstractExpansionCard;
import expansioncontent.expansionContentMod;
import hermit.cards.EternalForm;
import sneckomod.actions.ChangeGoldAction;

import java.util.ArrayList;

import static com.megacrit.cardcrawl.dungeons.AbstractDungeon.cardRandomRng;
import static expansioncontent.cards.AbstractExpansionCard.makeID;

public class HeartsFavorWish extends AbstractDownfallCard {

    public static final String ID = makeID("HeartsFavorWish");
    private static final CardStrings cardStrings;
    public static final String IMG_PATH = expansionContentMod.makeCardPath("BloodySacrifice.png");
    public HeartsFavorWish() {
        super(ID, cardStrings.NAME, IMG_PATH, 0, cardStrings.DESCRIPTION, CardType.SKILL, CardColor.COLORLESS, CardRarity.SPECIAL, CardTarget.SELF);
        isEthereal = true;
        exhaust = true;
    }

    public boolean checkBossFaced(String id){
        if (downfallMod.Act1BossFaced.equals(id)) return true;
        if (downfallMod.Act2BossFaced.equals(id)) return true;
        if (downfallMod.Act3BossFaced.equals(id)) return true;
        return false;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractCard> stanceChoices = new ArrayList();

        if (checkBossFaced("downfall:Ironclad")) stanceChoices.add(new DemonForm());
        if (checkBossFaced("downfall:Silent")) stanceChoices.add(new WraithForm());
        if (checkBossFaced("downfall:Defect")) stanceChoices.add(new EchoForm());
        if (checkBossFaced("downfall:Watcher")) stanceChoices.add(new DevaForm());
        if (checkBossFaced("downfall:Hermit")) stanceChoices.add(new EternalForm());

        if (stanceChoices.size() < 3){
            //for debugging when jumping straight to the boss, skipping the actual bosses
            stanceChoices.clear();
            stanceChoices.add(new WraithForm());
            stanceChoices.add(new EchoForm());
            stanceChoices.add(new EternalForm());
        }

        addToBot(new FlexibleDiscoveryAction(stanceChoices, true));

        int choice;
        for (int i = 0; i < 3; i++) {
            choice= cardRandomRng.random(0,4);
            switch (choice) {
                case 0:AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Aged(), 1)); break;
                case 1:AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Bewildered(), 1)); break;
                case 2:AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Scatterbrained(), 1)); break;
                case 3:AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Icky(), 1)); break;
                case 4:AbstractDungeon.actionManager.addToBottom(new MakeTempCardInDiscardAction(new Malfunctioning(), 1)); break;

            }

        }
    }


    public void upgrade() {
        if (!upgraded) {
            upgradeName();
        }
    }
    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
