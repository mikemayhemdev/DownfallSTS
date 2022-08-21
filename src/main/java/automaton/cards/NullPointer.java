package automaton.cards;

import automaton.AutomatonMod;
import automaton.cardmods.UnplayableMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.cards.status.Slimed;
import com.megacrit.cardcrawl.cards.status.Wound;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;

import static automaton.AutomatonMod.makeBetaCardPath;

public class NullPointer extends AbstractBronzeCard {

    public final static String ID = makeID("NullPointer");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 13;
    private static final int UPG_DAMAGE = 5;

    private static final int BLOCK = 13;
    private static final int UPG_BLOCK = 5;

    public NullPointer() {
        super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("NullPointer.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            atb(new VFXAction(new ViceCrushEffect(m.hb.cX, m.hb.cY), 0.5F));
        blck();
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);

        shuffleIn(new Burn());
        shuffleIn(new Wound());
    }


    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}
