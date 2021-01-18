package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;
import slimebound.vfx.LeechEffect;


public class LeechLife extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:LeechLife";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/leechingstrike.png";
    private static final CardType TYPE = CardType.ATTACK;
    private static final CardRarity RARITY = CardRarity.RARE;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardStrings cardStrings;
    private static final int COST = 2;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;

    }


    public LeechLife() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), COST, DESCRIPTION, TYPE, AbstractCardEnum.SLIMEBOUND, RARITY, TARGET);
        this.baseDamage = 12;
        this.exhaust = true;
        tags.add(CardTags.HEALING);
    }

    @Override
    public void triggerOnGlowCheck() {
        slimedGlowCheck();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        int x = 0;
        if (m.hasPower(SlimedPower.POWER_ID)) {
           x = m.getPower(SlimedPower.POWER_ID).amount;
        }
        AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new com.megacrit.cardcrawl.cards.DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));

        if (x>0) {
            AbstractDungeon.actionManager.addToBottom(new VFXAction(new LeechEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, x, new Color(0.75F, 0F, 0F, 1F)), 0.15F));
            AbstractDungeon.actionManager.addToBottom(new HealAction(AbstractDungeon.player, AbstractDungeon.player, x));
        }
    }

    public AbstractCard makeCopy() {

        return new LeechLife();

    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(6);
        }
    }
}


