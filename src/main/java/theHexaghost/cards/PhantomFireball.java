package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theHexaghost.HexaMod;
import theHexaghost.powers.BurnPower;
import theHexaghost.vfx.ExplosionSmallEffectGreen;

public class PhantomFireball extends AbstractHexaCard {

    public final static String ID = makeID("PhantomFireball");

    //stupid intellij stuff ATTACK, ENEMY, UNCOMMON

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;

    public PhantomFireball() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        HexaMod.loadJokeCardImage(this, "PhantomFireball.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, makeInfo(), AbstractGameAction.AttackEffect.FIRE);
        if (m.hasPower(BurnPower.POWER_ID)){
            if (m.getPower(BurnPower.POWER_ID).amount > 1){

                att(new AbstractGameAction() {
                    @Override
                    public void update() {
                        if (m.hasPower(BurnPower.POWER_ID)) {
                            BurnPower p = (BurnPower) m.getPower(BurnPower.POWER_ID);
                            p.explode(true, upgraded);
                        }
                        this.isDone = true;
                    }
                });
            }
        }
    }

    @Override
    public void triggerOnGlowCheck() {
        this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();

        if (AbstractDungeon.getCurrRoom().monsters != null)
            for (AbstractMonster m : AbstractDungeon.getCurrRoom().monsters.monsters) {
                if (!m.isDeadOrEscaped() && m.hasPower(BurnPower.POWER_ID)) {
                    if (upgraded){
                        if (m.getPower(BurnPower.POWER_ID).amount > 1) {
                            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                            break;
                        } else {
                            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
                            break;
                        }
                    }
                }
            }
    }

    @Override
    public float getTitleFontSize() {
        if(Settings.language != Settings.GameLanguage.ENG) {
            return 19.0F;
        } else {
            return 23.0F;
        }
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPG_DAMAGE);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}