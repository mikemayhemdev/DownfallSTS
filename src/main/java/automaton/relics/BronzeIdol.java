package automaton.relics;

import automaton.AutomatonMod;
import automaton.cards.goodstatus.*;
import basemod.helpers.CardPowerTip;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.cards.AbstractCard;
import downfall.util.TextureLoader;
import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;

import static automaton.AutomatonMod.makeRelicOutlinePath;
import static automaton.AutomatonMod.makeRelicPath;

public class BronzeIdol extends CustomRelic {

    public static final String ID = AutomatonMod.makeID("BronzeIdol");
    private static final Texture IMG = TextureLoader.getTexture(makeRelicPath("BronzeIdol.png"));
    private static final Texture OUTLINE = TextureLoader.getTexture(makeRelicOutlinePath("BronzeIdol.png"));
    private float rotationTimer;
    private ArrayList<AbstractCard> card_list = new ArrayList<>();
    private int previewIndex = 0;
    private CardPowerTip card_tip = new CardPowerTip(null);

    public BronzeIdol() {
        super(ID, IMG, OUTLINE, RelicTier.SHOP, LandingSound.MAGICAL);
        card_list.add(new Daze());
        card_list.add(new UsefulSlime());
        card_list.add(new Ignite());
        card_list.add(new GrievousWound());
        card_list.add(new IntoTheVoid());
        card_tip.card = card_list.get(0);
        tips.add(card_tip);
    }


    @Override
    public void update() {
        super.update();
        if (hb.hovered) {
            if (rotationTimer <= 0F ) {
                rotationTimer = 2F;
                card_tip.card = card_list.get(previewIndex);
                if (++previewIndex >= card_list.size()) {
                    previewIndex = 0;
                }
            } else {
                rotationTimer -= Gdx.graphics.getDeltaTime();
            }
        }
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
