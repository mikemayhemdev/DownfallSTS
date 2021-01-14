package champ.cards;

import basemod.helpers.VfxBuilder;
import champ.powers.BoomerangPower;
import downfall.util.TextureLoader;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class CrownThrow extends AbstractChampCard {

    public final static String ID = makeID("CrownThrow");

    //stupid intellij stuff attack, enemy, rare

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;

    public CrownThrow() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_HEAVY);
        // First you throw a crown...
        AbstractDungeon.effectList.add(new VfxBuilder(TextureLoader.getTexture("champResources/images/relics/ChampionCrown.png"), p.hb.x + p.hb.width, p.hb.cY, 1.5F)
                .moveX(p.hb.x + p.hb.width, Settings.WIDTH + (128 * Settings.scale))
                .rotate(-300F)
                .build());
        if (this.costForTurn != 0 && !freeToPlayOnce) {
            applyToSelf(new BoomerangPower(this));
        }
    }

    public void upp() {
        upgradeDamage(UPG_DAMAGE);
    }
}