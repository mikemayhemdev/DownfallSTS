package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GetAllInBattleInstances;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;

import java.util.Iterator;


public class GrowthPunch extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:GrowthPunch";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/slimepunch.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    private static final int POWER = 6;
    private static final int UPGRADE_BONUS = 3;
    public static String UPGRADED_DESCRIPTION;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }

    private int increaseAmount;


    public GrowthPunch() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        //this.misc = 3;

        this.magicNumber = this.baseMagicNumber = 1;
        this.increaseAmount = this.magicNumber;

        this.exhaust = true;

        this.baseDamage = 6 + this.misc;
        this.baseBlock = 1 + this.misc;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        if (upgraded)
            AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.common.GainBlockAction(p, p, this.block));
        this.increaseAmount = this.magicNumber;

        Iterator var1 = AbstractDungeon.player.masterDeck.group.iterator();

        AbstractCard c;
        while (var1.hasNext()) {
            c = (AbstractCard) var1.next();
            if (c.uuid.equals(this.uuid)) {
                c.misc += this.increaseAmount;
                c.applyPowers();
                c.baseDamage = 6 + c.misc;
                c.baseBlock = 1 + c.misc;

                c.isDamageModified = false;
            }
        }

        for (var1 = GetAllInBattleInstances.get(this.uuid).iterator(); var1.hasNext(); c.baseDamage = 6 + c.misc) {
            c = (AbstractCard) var1.next();
            c.misc += this.increaseAmount;
            //c.applyPowers();
            c.baseDamage = 6 + c.misc;
            c.baseBlock = 1 + c.misc;
        }

    }

    public AbstractCard makeCopy() {

        return new GrowthPunch();

    }

    public void applyPowers() {
        this.baseDamage = 6 + this.misc;
        this.baseBlock = 1 + this.misc;
        super.applyPowers();
        this.initializeDescription();
    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();

            //upgradeMagicNumber(1);

        }

    }
}


