package automaton.cards;

import automaton.actions.TimedVFXAction;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import downfall.util.TextureLoader;

import java.util.ArrayList;

public class FormatEncoded extends AbstractBronzeCard {

    public final static String ID = makeID("FormatEncoded");

    //stupid intellij stuff attack, all, rare

    private static final int DAMAGE = 5;
    private static final int BLOCK = 5;

    public FormatEncoded() {
        super(ID, 1, CardType.ATTACK, CardRarity.SPECIAL, CardTarget.ENEMY, CardColor.COLORLESS);
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        thisEncodes();
    }

    public static Color getRandomColor() {
        ArrayList<Color> colorList = new ArrayList<>();
        colorList.add(Color.RED);
        colorList.add(Color.BLUE);
        colorList.add(Color.GREEN);
        colorList.add(Color.PURPLE);
        colorList.add(Color.WHITE);
        colorList.add(Color.NAVY);
        return colorList.get(AbstractDungeon.cardRandomRng.random(colorList.size() - 1)).cpy();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, block));
        if (m != null)
            atb(new TimedVFXAction(new VfxBuilder(TextureLoader.getTexture("bronzeResources/images/ui/block.png"), m.hb.x - (50 * Settings.scale), m.hb.cY, 0.1F)
                    .setColor(getRandomColor())
                    .fadeIn(0.1F)
                    .rotate(300f)
                    .moveX(m.hb.x - (50 * Settings.scale), m.hb.x - (150 * Settings.scale))
                    .andThen(0.1f)
                    .moveX(m.hb.x - (150 * Settings.scale), m.hb.cX)
                    .build())
            );
        dmg(m, AbstractGameAction.AttackEffect.BLUNT_LIGHT);
    }

    public void upp() {
        upgradeDamage(2);
        upgradeBlock(2);
    }
}