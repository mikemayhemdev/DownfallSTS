package slimebound.cards;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.powers.SlimedPower;
import slimebound.vfx.SlimeSlapEffect;


public class SlimeSlap extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:SlimeSlap";
    public static final String IMG_PATH = SlimeboundMod.getResourcePath("cards/slimeslap.png");
    private static final CardStrings cardStrings;

    public SlimeSlap() {
        super(ID, cardStrings.NAME, IMG_PATH, 2, cardStrings.DESCRIPTION, CardType.ATTACK, AbstractCardEnum.SLIMEBOUND, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = damage = 8;
        SlimeboundMod.loadJokeCardImage(this, "SlimeSlap.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null){
            addToBot(new AbstractGameAction() {
                @Override
                public void update() {
                    AbstractPower po = m.getPower(SlimedPower.POWER_ID);
                    if (po != null) ((SlimedPower) po).dontRemoveOnce = true;
                    this.isDone = true;
                }
            });
        }
        addToBot(new VFXAction(new SlimeSlapEffect(m.hb.cX, m.hb.cY), 0.2F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_LIGHT));
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        slimedGlowCheck();
    }

    public AbstractCard makeCopy() {
        return new SlimeSlap();
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}

