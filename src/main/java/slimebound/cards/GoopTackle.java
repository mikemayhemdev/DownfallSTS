package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.actions.TackleSelfDamageAction;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.TackleBuffPower;
import slimebound.powers.TackleDebuffPower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;


public class GoopTackle extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:GoopTackle";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/corrosivetackle.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }

    public GoopTackle() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        tags.add(SlimeboundMod.TACKLE);


        this.baseDamage = 12;
        baseSelfDamage = this.selfDamage = 3;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        addToBot(new TackleSelfDamageAction(new DamageInfo(p, selfDamage, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SMASH));

        ArrayList<AbstractCard> qCardList = new ArrayList<>();
        for (AbstractCard q : CardLibrary.getAllCards()) {
            if (q.hasTag(SlimeboundMod.TACKLE) && !(q instanceof GoopTackle)) {
                qCardList.add(q.makeCopy());
            }
        }


        AbstractCard cTackle = qCardList.get(AbstractDungeon.cardRng.random(0, qCardList.size() - 1));
        if (upgraded) {
            cTackle.upgrade();
        }

        cTackle.setCostForTurn(0);

        AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(cTackle));

        //AbstractDungeon.actionManager.addToBottom(new RemoveSpecificPowerAction(p,p,TackleBuffPower.POWER_ID));


    }

    public AbstractCard makeCopy() {

        return new GoopTackle();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeDamage(3);

            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

        }

    }
}


