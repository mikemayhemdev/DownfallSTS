package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostAction;
import com.megacrit.cardcrawl.actions.common.ReduceCostForTurnAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;

import java.util.ArrayList;


public class Chomp extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:Chomp";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/chomp.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 1;
    public static String UPGRADED_DESCRIPTION;
    public static int originalDamage;
    public static int originalBlock;
    public static int upgradeDamage;
    public static int upgradeSelfDamage;
    private static int baseSelfDamage;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;
        UPGRADED_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    }


    public Chomp() {

        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);


        this.baseDamage = originalDamage = 8;
        upgradeDamage = 2;
        // this.exhaust = true;

        this.magicNumber = this.baseMagicNumber = 3;


    }

    public void use(AbstractPlayer p, AbstractMonster m) {


        AbstractDungeon.actionManager.addToBottom(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY, Color.GREEN), 0.2F));

        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));

        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                ArrayList<AbstractCard> tackleList = new ArrayList<>();
                for (AbstractCard q : AbstractDungeon.player.hand.group) {
                    if (q.hasTag(SlimeboundMod.TACKLE) && q.costForTurn > 0) {
                        tackleList.add(q);
                    }
                }
                if (!tackleList.isEmpty()) {
                    if (upgraded){
                        tackleList.get(AbstractDungeon.cardRandomRng.random(tackleList.size() - 1)).modifyCostForCombat(-9);


                    } else {
                        addToTop(new ReduceCostForTurnAction(tackleList.get(AbstractDungeon.cardRandomRng.random(tackleList.size() - 1)), 9));
                    }
                }
            }
        });
    }

    public AbstractCard makeCopy() {

        return new Chomp();

    }

    public void upgrade() {

        if (!this.upgraded) {

            upgradeName();

            upgradeDamage(upgradeDamage);
            this.rawDescription = UPGRADED_DESCRIPTION;
            this.initializeDescription();


        }

    }
}


