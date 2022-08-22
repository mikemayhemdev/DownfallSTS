package automaton.cards;

import automaton.AutomatonMod;
import automaton.actions.AddToFuncAction;
import automaton.cardmods.EncodeMod;
import automaton.powers.NextFunctionFreePower;
import automaton.powers.NextFunctionNoExhaustPower;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import guardian.vfx.BronzeOrbEffect;

import java.util.ArrayList;

import static automaton.AutomatonMod.makeBetaCardPath;

public class BronzeOrb extends AbstractBronzeCard {

    public final static String ID = makeID("BronzeOrb");

    //stupid intellij stuff attack, enemy, uncommon

    private static final int DAMAGE = 5;
    private static final int UPG_DAMAGE = 4;

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 3;

    public BronzeOrb() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
       baseBlock = BLOCK;
       baseMagicNumber = magicNumber = 1;
        exhaust = true;
        isInnate = true;
        AutomatonMod.loadJokeCardImage(this, makeBetaCardPath("BronzeOrb.png"));
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        atb(new VFXAction(new BronzeOrbEffect(p, m), 0.5F));
        blck();
        applyToSelf(new NextFunctionFreePower(magicNumber));
    }

    public void upp() {
        upgradeMagicNumber(1);
      //  upgradeBlock(UPG_BLOCK);
    }
}