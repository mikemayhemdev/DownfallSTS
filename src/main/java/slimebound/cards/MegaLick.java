package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.WeakPower;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;
import slimebound.vfx.LickEffect;
import slimebound.vfx.SlimeDripsEffect;

import static com.badlogic.gdx.graphics.Color.GREEN;


public class MegaLick extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:MegaLick";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/megalick.png";
    private static final CardType TYPE = CardType.SKILL;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ALL_ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 0;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public MegaLick() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        tags.add(SlimeboundMod.LICK);


        this.magicNumber = this.baseMagicNumber = 1;

        this.slimed = this.baseSlimed = 4;
        this.exhaust = true;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
                if ((!monster.isDead) && (!monster.isDying)) {

                    AbstractDungeon.effectsQueue.add(new SlimeDripsEffect(monster.hb.cX, monster.hb.cY, 3));
                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new WeakPower(monster, this.magicNumber, false), this.magicNumber, true, AbstractGameAction.AttackEffect.NONE));

                    AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(monster, p, new SlimedPower(monster, p, this.slimed), this.slimed, true, AbstractGameAction.AttackEffect.NONE));
                    AbstractDungeon.actionManager.addToBottom(new VFXAction(new LickEffect(monster.hb.cX, monster.hb.cY, 0.6F, new Color(GREEN)), 0.1F));


                }
            }

        }
        if (upgraded)upgradeAction(p,m);

    }


    public void upgradeAction(AbstractPlayer p, AbstractMonster m){
        AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p, 1));
    }
    public AbstractCard makeCopy() {

        return new MegaLick();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }

    }
}


