package slimebound.cards;



import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.SlimepotheosisAction;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class CheckThePlaybook extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:CheckThePlaybook";
    public static final String NAME;
    private static final CardStrings cardStrings;
    public static final String DESCRIPTION;
    public static final String[] EXTENDED_DESCRIPTION;
    public static String UPGRADED_DESCRIPTION;
    public static final String IMG_PATH = "cards/playbook.png";

    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.SELF;

    private static final int COST = 2;
    private int numEaten = 0;
    private static final int BLOCK = 5;
    private static final int UPGRADE_BONUS = 3;


    public CheckThePlaybook() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.exhaust = true;


    }


    public void use(AbstractPlayer p, AbstractMonster m) {


        ArrayList<String> tmp = new ArrayList();
        Iterator var3 = CardLibrary.cards.entrySet().iterator();

        for (int i = 0; i < 4; i++) {
            while (var3.hasNext()) {
                Map.Entry<String, AbstractCard> c = (Map.Entry) var3.next();
                if (c.getValue().hasTag(SlimeboundMod.TACKLE)) {
                    tmp.add(c.getKey());

                }
            }

            AbstractCard cTackle = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
            cTackle = cTackle.makeCopy();
            cTackle.modifyCostForCombat(-1);
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cTackle));

        }
        /* FILL version

          int space = BaseMod.MAX_HAND_SIZE - p.hand.size();

        if (space > 0) {
            for (int i = 0; i <= space; i++) {
                while (var3.hasNext()) {
                    Map.Entry<String, AbstractCard> c = (Map.Entry) var3.next();
                    if (c.getValue().hasTag(SlimeboundMod.TACKLE)) {
                            tmp.add(c.getKey());

                    }
                }

                AbstractCard cTackle = CardLibrary.cards.get(tmp.get(AbstractDungeon.cardRng.random(0, tmp.size() - 1)));
                cTackle = cTackle.makeCopy();
                cTackle.modifyCostForCombat(-1);
                AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cTackle));

            }
        }
        */

    }


    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
        EXTENDED_DESCRIPTION = cardStrings.EXTENDED_DESCRIPTION;
    }

    public AbstractCard makeCopy() {
        return new CheckThePlaybook();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);


        }
    }
}

