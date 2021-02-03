package automaton.cards;

import automaton.AutomatonMod;
import automaton.cardmods.UnplayableMod;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.ViceCrushEffect;

public class NullPointer extends AbstractBronzeCard {

    public final static String ID = makeID("NullPointer");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 12;
    private static final int UPG_DAMAGE = 3;

    private static final int BLOCK = 12;
    private static final int UPG_BLOCK = 3;

    public NullPointer() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        thisEncodes();
        tags.add(AutomatonMod.BAD_COMPILE);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null)
            atb(new VFXAction(new ViceCrushEffect(m.hb.cX, m.hb.cY), 0.5F));
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        blck();
    }

    @Override
    public void onCompile(AbstractCard function, boolean forGameplay) {
        CardModifierManager.addModifier(function, new UnplayableMod());
    }

    @Override
    public void onCompileLast(AbstractCard function, boolean forGameplay) {
        function.cost = -2;
        function.costForTurn = -2;
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
        upgradeBlock(UPG_BLOCK);
    }
}