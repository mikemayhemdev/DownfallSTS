package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FireballEffect;
import theHexaghost.powers.BurnPower;

public class Sear extends AbstractHexaCard {

    public final static String ID = makeID("Sear");

    //stupid intellij stuff SKILL, ENEMY, BASIC

    private static final int MAGIC = 10;

    public Sear() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseBurn = burn = MAGIC;
        isEthereal = true;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new FireballEffect(p.hb.cX, p.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
        burn(m, burn);
    }

    @Override
    public void triggerOnExhaust() {
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                AbstractMonster m = AbstractDungeon.getRandomMonster();
                att(new ApplyPowerAction(m, AbstractDungeon.player, new BurnPower(m, burn), burn));
                att(new VFXAction(new FireballEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, m.hb.cX, m.hb.cY), 0.5F));
            }
        });

    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBurn(6);
        }
    }
}