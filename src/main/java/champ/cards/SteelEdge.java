package champ.cards;

import automaton.actions.EasyXCostAction;
import champ.ChampMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.GoldenSlashEffect;

import static champ.ChampMod.loadJokeCardImage;

public class SteelEdge extends AbstractChampCard {
    public final static String ID = makeID("SteelEdge");

    public SteelEdge() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 6;
        this.tags.add(ChampMod.FINISHER);
        postInit();
        loadJokeCardImage(this, "SteelEdge.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.player.useJumpAnimation();

        atb(new EasyXCostAction(this, (effect, params) -> {
            for (int i = 0; i < effect; i++) {
                atb(new VFXAction(new GoldenSlashEffect(m.hb.cX + 30.0F * Settings.scale, m.hb.cY, true), 0.1F));
                dmg(m, AbstractGameAction.AttackEffect.NONE);
                if ((i < effect-1) && (effect !=0)){
                    finisher(true);
                }
            }
            return true;
        }));
        finisher();
    }

    public void upp() {
        upgradeDamage(3);
    }
}