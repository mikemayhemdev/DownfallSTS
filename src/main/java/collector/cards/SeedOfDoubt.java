package collector.cards;

import collector.powers.DoomPower;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GiantEyeEffect;

import static collector.CollectorMod.makeID;
import static collector.util.Wiz.applyToEnemy;

public class SeedOfDoubt extends AbstractCollectorCard implements OnOtherCardExhaustInHand {
    public final static String ID = makeID(SeedOfDoubt.class.getSimpleName());
    // intellij stuff skill, enemy, uncommon, , , , , 4, 2

    public SeedOfDoubt() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
        baseSecondMagic = secondMagic = 1;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new VFXAction(new GiantEyeEffect(m.hb.cX, m.hb.cY + 300.0F * Settings.scale, new Color(1.0F, 0.3F, 1.0F, 0.0F))));
        applyToEnemy(m, new DoomPower(m, magicNumber));
    }

    @Override
    public void onOtherCardExhaustWhileInHand(AbstractCard card) {
        flash(Color.PURPLE.cpy());
        baseMagicNumber += secondMagic;
        magicNumber = baseMagicNumber;
    }

    public void upp() {
        upgradeSecondMagic(1);
    }
}