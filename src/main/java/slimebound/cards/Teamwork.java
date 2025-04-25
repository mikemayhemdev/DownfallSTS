package slimebound.cards;


import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import slimebound.SlimeboundMod;
import slimebound.actions.CoordinateAction;
import slimebound.patches.AbstractCardEnum;


public class Teamwork extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Teamwork";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/coordinatedstrike.png");
    private static final CardStrings cardStrings;

    public Teamwork() {
        super(ID, cardStrings.NAME, IMG_PATH, -1, cardStrings.DESCRIPTION, CardType.SKILL, AbstractCardEnum.SLIMEBOUND, CardRarity.RARE, CardTarget.SELF);
        baseBlock = 5;
        SlimeboundMod.loadJokeCardImage(this, "Teamwork.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (this.energyOnUse < EnergyPanel.totalCount) {
            this.energyOnUse = EnergyPanel.totalCount;
        }
        AbstractDungeon.actionManager.addToBottom(new CoordinateAction(p, m, this.magicNumber, this.freeToPlayOnce, this.energyOnUse, block, false));

       // checkMinionMaster();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(3);
        }
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}


